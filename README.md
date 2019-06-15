# OpenImgurClient Android
### NOT AFFILIATED WITH OR APPROVED BY IMGUR

Open source Android client for browsing [imgur](https://imgur.com) image service using [Imgur API](https://apidocs.imgur.com/). Serves as a showcase for the great Imgur API and Android Jetpack best practices.

Build using [Android Jetpack libraries](https://developer.android.com/jetpack)
- Kotlin and Kotlin Coroutines
- MVVM Architechture with repository fetching data from Room database and/or Retrofit rest-api-client
- Databinding, LiveData, ViewModel, Paging
- Navigation library

## Building and running
To build, add variable `IMGUR_CLIENT_ID=<YOUR-ID-HERE>` in your `gradle.properties`.
Generate your api key here: https://imgur.com/account/settings/apps


## License

### If licence is not included in the individual file, the following is in effect:

MIT License

Copyright (c) 2019 Ville Valta

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
