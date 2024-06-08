DO
$do$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_catalog.pg_roles
      WHERE  rolname = 'admin') THEN
      CREATE ROLE "admin";
   END IF;
END
$do$;

GRANT USAGE ON SCHEMA public TO "admin";

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO "admin";

GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO "admin";

DO
$do$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_catalog.pg_roles
      WHERE  rolname = 'user') THEN
      CREATE ROLE "user";
   END IF;
END
$do$;

GRANT USAGE ON SCHEMA public TO "user";

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO "user";

GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO "user";

DO
$do$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_catalog.pg_policies
      WHERE  policyname = 'Public users are viewable by everyone.') THEN
      CREATE POLICY "Public users are viewable by everyone." ON public."User" FOR SELECT USING (true);
   END IF;
END
$do$;

DO
$do$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_catalog.pg_policies
      WHERE  policyname = 'Users can insert their own users.') THEN
      CREATE POLICY "Users can insert their own users." ON public."User" FOR INSERT WITH CHECK (auth.uid()::text = id);
   END IF;
END
$do$;

DO
$do$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_catalog.pg_policies
      WHERE  policyname = 'Users can update own users or update invited not valid users') THEN
      CREATE POLICY "Users can update own users or update invited not valid users" ON public."User" FOR UPDATE USING (((SELECT invited_at FROM auth.users AS au WHERE au.id::text = "User".id) IS NOT NULL) OR (auth.uid()::text = id));
   END IF;
END
$do$;

DO
$do$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_catalog.pg_policies
      WHERE  policyname = 'Users are viewable by users who created them.') THEN
      CREATE POLICY "Users are viewable by users who created them." ON public."User" FOR SELECT USING (auth.uid()::text = id);
   END IF;
END
$do$;

CREATE OR REPLACE FUNCTION public.handle_new_user()
  RETURNS TRIGGER
  LANGUAGE 'plpgsql'
  COST 100
  VOLATILE NOT LEAKPROOF SECURITY DEFINER
  SET search_path = public
AS $$
BEGIN
  IF (new.raw_app_meta_data->>'provider' = 'email') THEN
    IF (((new.invited_at IS NOT NULL) AND (old.last_sign_in_at IS NULL))) THEN
      INSERT INTO public."User" (id, "createdAt", "updatedAt", username, role, "firstName", "lastName")
      VALUES (new.id::text, new.created_at, new.updated_at, new.email, ARRAY[new.role], new.raw_user_meta_data->>'firstName', new.raw_user_meta_data->>'lastName')
      ON CONFLICT (id)
      DO UPDATE SET "createdAt" = new.created_at, "updatedAt" = new.updated_at, username = new.email, role = new.role, "firstName" = new.raw_user_meta_data->>'firstName', "lastName" = new.raw_user_meta_data->>'lastName';
    ELSE
      INSERT INTO public."User" (id, "createdAt", "updatedAt", username, role, "firstName", "lastName")
      VALUES (new.id::text, new.created_at, new.updated_at, new.email, ARRAY[new.role], new.raw_user_meta_data->>'firstName', new.raw_user_meta_data->>'lastName')
      ON CONFLICT (id)
      DO UPDATE SET "createdAt" = new.created_at, "updatedAt" = new.updated_at, username = new.email, role = new.role, "firstName" = new.raw_user_meta_data->>'firstName', "lastName" = new.raw_user_meta_data->>'lastName';
    END IF;
  ELSE
    IF new.role = 'authenticated' THEN
      UPDATE auth.users SET role = 'user' WHERE id = new.id;
      new.role := 'user';
      RETURN NEW;
    END IF;
    INSERT INTO public."User" (id, "createdAt", "updatedAt", "firstName", "lastName", username, role)
    VALUES (new.id::text, new.created_at, new.updated_at, new.raw_user_meta_data->>'firstName', new.raw_user_meta_data->>'lastName', new.email, ARRAY[new.role])
    ON CONFLICT (id)
    DO UPDATE SET "createdAt" = new.created_at, "updatedAt" = new.updated_at, "firstName" = new.raw_user_meta_data->>'firstName', "lastName" = new.raw_user_meta_data->>'lastName', username = new.email, role = new.role;
  END IF;
  RETURN NEW;
END
$$;

CREATE OR REPLACE FUNCTION public.handle_delete_user()
  RETURNS TRIGGER
  LANGUAGE plpgsql
  SECURITY DEFINER
  SET search_path = public
AS $$
BEGIN
  DELETE FROM public."User" WHERE id = old.id::text;
  RETURN OLD;
EXCEPTION
  WHEN others THEN
    RAISE WARNING 'Error occurred during user deletion: %', SQLERRM;
    RETURN NULL;
END
$$;

DO
$do$
BEGIN
IF NOT EXISTS (
  SELECT FROM pg_catalog.pg_user
  WHERE  usename = 'user_postgres') THEN
  CREATE USER user_postgres WITH LOGIN PASSWORD 'user_postgres';
END IF;
END
$do$;

GRANT SELECT ON auth.users TO "user_postgres";

DO
$do$
BEGIN
IF NOT EXISTS (
  SELECT FROM pg_catalog.pg_roles
  WHERE  rolname = 'user') THEN
  CREATE ROLE "user" LOGIN NOINHERIT;
END IF;
END;
$do$;

grant "user" to authenticator;
GRANT USAGE ON SCHEMA public TO "user";
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO "user";

CREATE OR REPLACE FUNCTION requesting_user_id()
  RETURNS TEXT
  LANGUAGE SQL STABLE
AS $$
  SELECT NULLIF(current_setting('request.jwt.claims', true)::json->>'sub', '')::TEXT;
$$;

CREATE OR REPLACE FUNCTION requesting_user_role()
  RETURNS TEXT
  LANGUAGE SQL STABLE
AS $$
  SELECT NULLIF(current_setting('request.jwt.claims', true)::json->>'role', '')::TEXT;
$$;

CREATE OR REPLACE TRIGGER on_auth_user_created
AFTER INSERT OR UPDATE ON auth.users
FOR EACH ROW EXECUTE PROCEDURE public.handle_new_user();

CREATE OR REPLACE TRIGGER on_auth_user_deleted
AFTER DELETE ON auth.users
FOR EACH ROW EXECUTE PROCEDURE public.handle_delete_user();
