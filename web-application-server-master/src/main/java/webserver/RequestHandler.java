package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static util.HttpRequestUtils.*;
import static util.IOUtils.readData;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        Map<String, String> headers = new HashMap<>();
        Map<String, String> cookies = new HashMap<>();

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            log.debug("[Request Header] ====> \n");
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = br.readLine();
            log.debug("request line : {}", line);

            if (line == null) {
                return;
            }

            String url = getUrl(line);
            line = br.readLine(); //request line 다음부터 headers line

            while (!"".equals(line)) {
                log.debug("header : {}", line);
                HttpRequestUtils.Pair header = parseHeader(line);
                if (header != null) {
                    headers.put(header.getKey(), header.getValue());
                }
                line = br.readLine();
            }

            if (headers.get("Cookie") != null) {
                cookies = parseCookies(headers.get("Cookie"));
            }
            log.debug("[*] Cookies : {}", cookies);

            DataOutputStream dos = new DataOutputStream(out);

            if ("/user/create".startsWith(url)) {
                // request body에서 사용자 입력값 parsing
                String requestBody = readData(br, Integer.parseInt(headers.get("Content-Length")));
                Map<String, String> params = parseQueryString(requestBody);
                createUser(params);
                response302Header(dos, "/index.html");
                return;
            }

            if ("/user/login ".startsWith(url)) { //login.html 페이지 요청과 구분 필요
                String requestBody = readData(br, Integer.parseInt(headers.get("Content-Length")));
                Map<String, String> params = parseQueryString(requestBody);
                log.debug("[*] request - userId : {}, password : {}", params.get("userId"), params.get("password"));
                User user = DataBase.findUserById(params.get("userId"));

                if (user == null || !user.getPassword().equals(params.get("password"))) {
                    responseLogin(dos,
                            false,
                            "/user/login_failed.html");
                    return;
                }

                responseLogin(dos,
                        true,
                        "/index.html");
                return;
            }

            if ("/user/list".startsWith(url)) {
                if (cookies == null || !cookies.get("logined").equals("true")) {
                    response302Header(dos, "/user/login.html");
                    return ;
                }
                Collection<User> users = DataBase.findAll();
                StringBuilder sb = new StringBuilder();
                sb.append("<table border='1'>");
                for (User user : users) {
                    sb.append("<tr>");
                    sb.append("<td>" + user.getUserId() + "</td>");
                    sb.append("<td>" + user.getName() + "</td>");
                    sb.append("<td>" + user.getEmail() + "</td>");
                    sb.append("</tr>");
                }
                sb.append("</table>");
                byte[] body = sb.toString().getBytes();
                response200Header(dos, body.length);
                responseBody(dos, body);
                return ;
            }

            if (url.endsWith(".css")) {
                byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
                response200CssHeader(dos, body.length);
                responseBody(dos, body);
                return ;
            }

            responseResource(dos, url);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseResource(DataOutputStream dos, String url) throws IOException {
        byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
        response200Header(dos, body.length);
        responseBody(dos, body);
    }

    private User createUser(Map<String, String> params) {
        User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        log.debug("[*] newUser : {}", user);
        DataBase.addUser(user);
        return user;
    }

    private String getUrl(String line) {
        return line.split(" ")[1];
    }

    private void responseLogin(DataOutputStream dos, boolean logined, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 OK \r\n");
            //TODO: cookie 옵션 추가 설정 - path
            dos.writeBytes("Set-Cookie: logined=" + logined + "\r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void response302Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 OK \r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            e.printStackTrace();
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

    private void response200CssHeader(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            e.printStackTrace();
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
