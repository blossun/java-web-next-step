# 실습을 위한 개발 환경 세팅
* https://github.com/slipp/web-application-server 프로젝트를 자신의 계정으로 Fork한다. Github 우측 상단의 Fork 버튼을 클릭하면 자신의 계정으로 Fork된다.
* Fork한 프로젝트를 eclipse 또는 터미널에서 clone 한다.
* Fork한 프로젝트를 eclipse로 import한 후에 Maven 빌드 도구를 활용해 eclipse 프로젝트로 변환한다.(mvn eclipse:clean eclipse:eclipse)
* 빌드가 성공하면 반드시 refresh(fn + f5)를 실행해야 한다.

# 웹 서버 시작 및 테스트
* webserver.WebServer 는 사용자의 요청을 받아 RequestHandler에 작업을 위임하는 클래스이다.
* 사용자 요청에 대한 모든 처리는 RequestHandler 클래스의 run() 메서드가 담당한다.
* WebServer를 실행한 후 브라우저에서 http://localhost:8080으로 접속해 "Hello World" 메시지가 출력되는지 확인한다.

# 각 요구사항별 학습 내용 정리
* 구현 단계에서는 각 요구사항을 구현하는데 집중한다. 
* 구현을 완료한 후 구현 과정에서 새롭게 알게된 내용, 궁금한 내용을 기록한다.
* 각 요구사항을 구현하는 것이 중요한 것이 아니라 구현 과정을 통해 학습한 내용을 인식하는 것이 배움에 중요하다. 

### 요구사항 1 - http://localhost:8080/index.html로 접속시 응답
### 자바 입출력

InputStreamReader의 생성자에 필요한 인자는 표준 입력을 통해 획득하고 BufferedReader의 생성자에서 필요한 인자는 InputStreamReader를 사용하면 된다.

결국 키보드에서 입력한 문자열은 키보드 버퍼에 정보를 저장해 두었다가 사용자가 입력을 마치면 문자열이 JVM에 전달되고 전달된 문자는 다시 System.in인 InputStream 객체로 저장된다.

다시 이는 InputStreamReader 객체를 생성하는데 사용되고 이어서 BufferedReader 클래스로 부터 객체를 생성하는데 사용되어 진다. BufferedReader 클래스에는 버퍼가 있기 때문에 문자열을 버퍼에 저장해 놓았다가 readLine() 메소드를 통해 한 줄을 한번에 읽어 들이게 되는 것이다.

```
Keyboard buffer -> inputStream -> InputStreamReader -> BufferedReader -> br.readLine()
```



### Java BufferedReader

소켓프로그래밍을 하거나 파일입출력을 할때 자바가 제공하는 스트림을 사용한다.

- (InputStream , OutputStream)

Byte 단위로 처리하기 때문에 영어나 숫자 등은 잘 처리하지만, 단위가 2바이트인 한글은 깨져서 출력된다.

그래서 char 단위인 InputStreamReader를 쓴다.

해당 클래스를 쓰면 한글도 잘 출력되지만, 한글자씩 받아와야 하는 상황이 아니면 버퍼에 저장하여 한꺼번에 받는 방식을 많이 사용한다.

```java
InputStream is = // inputStream 초기화는 상황마다 틀리므로, 초기화됬다 가정한다.
String temp;
BufferedReader buffer = new BufferReader(new InputStreamReader(is,"UTF-8");
while((temp = buffer.readLine() ) != null){
	//........
}
```

- 주의 할 점

readLine()은 개행문자가 포함되어야 내부 blocking이 풀리면서 wihle문이 실행한다는 것이다.

다시 말하자면,

BufferReader의 readLine() 를 쓸때는 inputStream 이 반드시 개행문자가 포함되어야 한다.

자바에서의 개행문자는 `"\n"` 이지만,

스트림에서의 개행문자는 `"\r\n"`이 개행문자이다.

따라서, 보내는쪽의 데이터 뒤에 `"\r\n"`을 반드시 붙여야한다.

## HTTP

웹 클라이언트(ex. 브라우저)는 웹 서버와 데이터를 주고 받기 위해 HTTP라는 규약을 따른다. 

**Request Header**

- 요청 라인(Request line)

    `HTTP-메소드 url HTTP-버전`

    - url : 클라이언트가 서버에 유일하게 식별할 수 있는 요청 자원의 경로
- 요청 헤더(Request Header)

    `<필드 이름> : <필드 값1>, <필드 값2>`

- 헤더와 본문 사이의 빈 공백 라인
- 요청 본문(Request Body)

**Response Body**

- 상태 라인(Status line)

    `HTTP-버전 상태코드 응답구문` 

- 응답 헤더(Reponse Header)
- 헤더와 본문 사이의 빈 공백 라인
- 응답 본문(Response Body)



### 요구사항 2 - get 방식으로 회원가입

* 

### 요구사항 3 - post 방식으로 회원가입
* 

### 요구사항 4 - redirect 방식으로 이동
* 

### 요구사항 5 - cookie
* 

### 요구사항 6 - stylesheet 적용
* 

### heroku 서버에 배포 후
* 
