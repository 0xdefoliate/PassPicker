# PassPicker
A simple, yet secure, password picker for Android

## Security

This app focuses on security, so of course there's a dedicated section for it.

### Signature

First off, before you use the app, please verify the APK with the excellent [AppVerifier](https://github.com/soupslurpr/AppVerifier) or `apksigner`.  
Otherwise, someone may have tampered with it if you aren't careful!

```
se.axelkarlsson.passpicker F0:0A:73:F7:71:F3:02:60:2D:26:F7:48:B1:23:1B:4F:3E:CE:42:FB:4F:CF:11:79:3E:ED:55:FE:55:1C:69:A3
```

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
* Generated passwords are hidden by default to defer shoulder-surfing risks.
* When a password is copied, they get hidden from the clipboard preview present in Android 13 and above.
