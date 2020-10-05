package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static util.HttpRequestUtils.parseQueryString;

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
            log.debug("[Request Header] ====> \n");
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = br.readLine();
            log.debug("request line : {}", line);

            if (line == null) {
                return ;
            }

            String url = getUrl(line);

            while (!"".equals(line)) {
                line = br.readLine();
                log.debug("header : {}", line);
            }

            if ("/user/create".startsWith(url)) {
                // URL에서 사용자 입력값 parsing
                int indexOfDelimeter = url.indexOf("?");
                String path = url.substring(0, indexOfDelimeter);
                String queryString = url.substring(indexOfDelimeter + 1);
                Map<String, String> params = parseQueryString(queryString);

                // parameter 정보로 User 생성
                User newUser = createUser(params);
            } else {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private User createUser(Map<String, String> params) {
        User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        log.debug("[*] newUser : {}", user);
        return user;
    }

    private String getUrl(String line) {
        return line.split( " ")[1];
//        String[] tokens = line.split( " ");
//        return tokens[1];
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
