https://code.google.com/archive/p/jianwikis/wikis/LdapAuthenticationInJava.wiki

jianwikis - LdapAuthenticationInJava.wiki

LDAP Authentication in Java

Usually, LDAP authentication is required for enterprise applications. Of course, you can use some LDAP Java library to achieve this.
But it is very easy to implement in simple Java code. Here, I like to illustrate how to implement this using Java and Spring with Ldaps support.
Authentication Mechanism

Ldap authentication usually first uses a manager account to bind to the Ldap server and this manager account should have the privilege
to search users in the Ldap server. Once the user is found, use the provided password to bind to that user.
The the bind is successful, the user is authenticated, otherwise, the authentication is failed.

