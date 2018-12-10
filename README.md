库的整合使用

allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
  
  
  
  dependencies {
	        compile 'com.github.xuanfengwuxiang:XuanFengWeather:1.2'
	}


//测试SSH提交不用密码