package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            /*
             * step 1
             * InputStream을 한 줄 단위로 읽기 위해 BufferedReader를 생성
             */
            log.debug("[Request Header] ====> \n");
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = br.readLine();
            log.debug("request line : {}", line);

            // line이 null 값인 경우에 대한 예외처리 - 무한 루프 방지
            if (line == null) {
                return ;
            }
            /*
             * step 2
             * 헤더 첫 라인에서 url 정보 파싱
             */
            String[] tokens = line.split( " ");
            String url = tokens[1];
//

            while (!"".equals(line)) {
                line = br.readLine();
                log.debug("header : {}", line);
            }

            /*
             * step 3
             * 요청 URL에 해당하는 파일을 webapp 디렉토리에서 읽어 전달
             */
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}

/*
 * RequestHandler는 Thread를 상속하고 있으며, 사용자의 요청에 대한 처리와 응답에 대한 처리를 담당
 * 앞으로 진행할 실습은 RequestHandler 클래스의 run() 메소드에서 구현
 * run() 메소드의 복잡도가 증가하는 경우 새로운 클래스, 메소드로 분리하는 방식으로 리팩토링을 하몉서 실습을 진행
 */
