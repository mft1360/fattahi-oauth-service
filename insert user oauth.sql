Insert into OAUTH_CLIENT_DETAILS
   (CLIENT_ID, CLIENT_SECRET, SCOPE, AUTHORIZED_GRANT_TYPES, AUTHORITIES, 
    ACCESS_TOKEN_VALIDITY, REFRESH_TOKEN_VALIDITY, AUTOAPPROVE)
 Values
   ('mohsen', '$2a$10$eablkpijBqWfi1v7S1W7iuAMYXPuQrFawjfFPyMz7ON0CxRJiQ43S', 'read,write', 'client_credentials,password,refresh_token,authorization_code', 'ROLE_TRUSTED_CLIENT', 
    86400, 108000, 'true');
COMMIT;



user table 
e0b766b04875ba02b82d90133bf2ecae7b4e76840e3179281a1123144cea4019
admin