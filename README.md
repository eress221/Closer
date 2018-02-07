# Closer
Android Wifi On/Off Icon


# 요약
스마트폰에서 와이파이 접속을 해제할 때 설정에서 와이파이 체크를 해제하면 되지만 설정화면으로 가거나 안드로이드 Status Bar를 내려서 하지 않고 바로 사용/해제 설정을 하기 위해 만들어졌습니다.


# 권한 설정
- SYSTEM_ALERT_WINDOW 권한을 사용합니다. 다른 어플 위로 화면을 표시하기 때문에 SYSTEM_ALERT_WINDOW 권한을 이용해서 Service로 화면을 계속 실행하는 방식이죠.
- Marshmallow(6.0, API 23) 버전 이후 권한체크가 강화됨에 따라 설정에서 다른 앱 위로 표시 가능 옵션을 체크 해야만 사용이 가능합니다. 체크하지 않으면 종료됩니다.
- Google Play Store를 통해 다운 받은 어플에 이 권한이 있다면 자동으로 옵션이 체크되어 있습니다. Store에 없거나 개발 시 이 권한을 사용한다면 옵션에 체크가 해제되어 있습니다.


# 라이브러리
- [Kotlin Android](https://kotlinlang.org/docs/tutorials/kotlin-android.html)


# License
'''
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
'''
