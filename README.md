# Closer
Android Wifi On/Off Icon

# 요약
일부 어플 사용시 강제로 로그인을 하는 경우 인터넷접속을 간단하게 해제하도록 개인적으로 사용하기 위해 만들었습니다.

스마트폰에서 와이파이 접속을 해제할 때 설정에서 와이파이 체크를 해제하면 되지만 설정화면으로 가거나 안드로이드 상태바를 내리지 않고 바로 사용/해제 설정을 하기 위해 만들어졌습니다.

어플을 다시 실행하거나 아이콘을 오른쪽으로 이동시키면 종료됩니다.

소스에는 화면이 있지만 사용하다보니 서비스만 실행하고 다른 동작이 없는 것이 더 간편해서 화면을 자동 종료하도록 했습니다.

# 권한 설정
- 다른 어플 위로 화면을 표시하기 위해 SYSTEM_ALERT_WINDOW 권한을 사용합니다.
- Marshmallow(6.0, API 23) 버전 이후 권한 체크가 강화됨에 따라 설정에서 다른 앱 위로 표시 가능 옵션(스마트폰마다 옵션명이 다릅니다.)을 활성화해야만 사용이 가능합니다. 활성화하지 않으면 어플이 중지됩니다.
- 일반적으로 Google Play Store에 등록된 어플에 이 권한이 있다면 자동으로 옵션이 활성화되어 있습니다. Google Play Store에 등록되지 않았거나 개발 도중 이 권한을 사용한다면 옵션이 비활성화되어 있습니다.

# Kotlin
공부용 개인 프로젝트이기 때문에 Kotlin을 연습할 겸 Java 프로젝트와 동일하게 Kotlin 프로젝트를 만들었습니다.

약간의 특이점으로는 Kotlin으로 만든 프로젝트가 Java로 만든 프로젝트보다 Apk 용량이 미세하게 많은 편입니다.

Kotlin의 좋은 점이 많기 때문에 능숙하게 사용한다면 개발이 좀더 쉬워질 것 같습니다. 또한 Kotlin과 Java를 한 프로젝트에 섞어서 사용해도 되고 Java 라이브러리도 문제없이 추가해서 사용할 수 있어 좋은 것 같습니다.

# 라이브러리
- [Kotlin Android](https://kotlinlang.org/docs/tutorials/kotlin-android.html)

# License
```
MIT License

Copyright (c) 2017  

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
