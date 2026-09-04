# PassPicker
A simple, yet secure, password picker for Android

## Security

This app focuses on security, so of course there's a dedicated section for it.

### Signature

First off, before you use the app, please verify the APK with the excellent [AppVerifier](https://github.com/soupslurpr/AppVerifier) or `apksigner`.  
Otherwise, someone may have tampered with it if you aren't careful!

```
se.axelkarlsson.passpicker 20:8A:DA:16:25:FD:A9:AE:96:FC:8D:04:3B:20:64:DE:AF:57:B4:88:38:7B:15:45:CC:8C:9D:10:D2:28:6F:D9
```

**NOTE:**
The signing keys were updated in `v1.2.0`, but if you want to verify older versions, please check the commit history of this file.
Please understand that the older signing keys were not compromised, and they were only rotated because I accidentally deleted them.

### Algorithm

Perhaps the most sensitive part, the password generation algorithm, is documented here for transparency.  

The generator derives passwords from Android's [`SecureRandom` API](https://developer.android.com/reference/java/security/SecureRandom), and in detail, the generation process works like this:
1. The app iterates *n* amount of times over the length the user inputted.
2. For each iteration, a generation option type (e.g. alphabetic, numeric, or special character) gets chosen securely with the aforementioned `SecureRandom` API. 
3. After an option type has been decided on, the app will query the option selected for a securely generated random character.
4. The character chosen in the above step gets appended.
5. Repeat until iteration is completed.

### UI/UX

Security efforts has also been put into the UI and UX.

Examples:
* Generated passwords are hidden by default to deter shoulder-surfing risks.
* When a password is copied, they get hidden from the clipboard preview present in Android 13 and above.
