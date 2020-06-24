
<h1>库的整合使用</h1>

<h3>网络框架和各种工具封装</h3>


allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
  
  
  
  dependencies {
	        implementation 'com.github.xuanfengwuxiang:XuanFengWeather:1.2.9'
	}


