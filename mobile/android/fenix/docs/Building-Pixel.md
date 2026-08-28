# Pixel 10 Pro용 Fenix 빌드

이 문서는 로컬 소스에서 Pixel 10 Pro용 Fenix APK를 빌드하고 설치하는 방법을 설명합니다.

## 준비

- Android Studio 또는 Android SDK
- `adb`가 포함된 Android SDK Platform Tools
- 휴대폰에서 개발자 옵션과 USB 디버깅 활성화
- 저장소 루트에서 실행

연결 상태를 확인합니다.

```bash
adb devices
```

기기 목록에 Pixel 10 Pro가 `device` 상태로 표시되어야 합니다.

## 최적화 APK 빌드

`nightly` 빌드는 R8 코드 최적화와 리소스 축소가 활성화된 로컬 릴리스 구성입니다. `benchmarkTest`는 Fenix APK의 ABI를 arm64-v8a로 제한합니다.

```bash
./mach gradle -p mobile/android/fenix app:assembleNightly -PbenchmarkTest
```

생성 파일:

```text
objdir-frontend/gradle/build/mobile/android/fenix/app/outputs/apk/nightly/app-arm64-v8a-nightly.apk
```

Pixel 10 Pro는 arm64-v8a 기기이므로 이 APK를 사용합니다. Android 기기별 CPU 명령어 최적화 APK를 별도로 만드는 방식은 사용하지 않으며, ART가 기기에서 추가 JIT/AOT 최적화를 수행합니다.

## 설치 및 실행

이 빌드는 공식 Nightly와 공존하도록 패키지 ID가 `org.mozilla.fenix.pixel`로 설정되어 있습니다.

```bash
adb install -r objdir-frontend/gradle/build/mobile/android/fenix/app/outputs/apk/nightly/app-arm64-v8a-nightly.apk
adb shell monkey -p org.mozilla.fenix.pixel -c android.intent.category.LAUNCHER 1
```

소스를 수정한 뒤에는 같은 `adb install -r` 명령으로 업데이트할 수 있습니다. 앱 데이터는 유지됩니다.

## 문제 해결

`INSTALL_FAILED_UPDATE_INCOMPATIBLE`가 나오면 기존의 `org.mozilla.fenix.pixel`만 제거한 뒤 다시 설치합니다. 공식 `org.mozilla.fenix`는 삭제하지 않아도 됩니다.

```bash
adb uninstall org.mozilla.fenix.pixel
adb install -r objdir-frontend/gradle/build/mobile/android/fenix/app/outputs/apk/nightly/app-arm64-v8a-nightly.apk
```

컴파일만 확인하려면 다음 명령을 사용합니다.

```bash
./mach gradle -p mobile/android/fenix app:compileNightlyKotlin -PbenchmarkTest
```
