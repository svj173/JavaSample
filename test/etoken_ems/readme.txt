
1) svj.jks - хранилище ключей.
  alias  - mykey
  Пароли ?
  - к хранилищу         - 123456
  - к приватному ключу  - 123456


= Symmetric key encryption uses the same key to encrypt and decrypt the message.

encrypt (plain text, key) = cipher text
decrypt (cipher text, key) = plain text


= Popular Types of Symmetric Encryption

Advanced Encryption Stardard (AES)
A good cipher, maybe the best.

Data Encryption Standard (DES)/3DES
The old stardard, key now to short.
Still OK if you us it 3 times.
Used in e-passports.

BlowFish
Like AES,

RC4: Rivest Cypter 4
Fast, used in SSL, WPA, problem is related keys are used in different sessions.


= Public Key Cryptography

Public Key Cryptography uses 2 keys:
- A public key for encryption
- A private key for decryption.

You can tell anyone you public and anyone can encrypt data just for you.

Only you can read the message.


= Types of Public Key Cryptography

Diffie-Hellman
- First public key system.
- Security based on the logs.

RSA
- Most common public key system.
- Security based on factoring large primes
- If in doubt use RSA

Elliptic Curve
- Based on curves in a finite field.


= Types of Hash Algorithm

SHA-1, SHA-2
- current standard, however it is possible to file two messages that have the same hash.

MD5
- often used for error checking can also find two files with the same hash.

