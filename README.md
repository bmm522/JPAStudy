<h2>김지인</h2>

<h2>테스트 방법</h2>
 {host}:8081/api/community 로 접속하시면 테스트가 가능합니다.
 {host}:8081/h2-console 로 접속하시면 DB를 조작하실 수 있습니다.
 테스트를 위해 3개의 멤버 데이터와 게시글 데이터가 들어가 있습니다.
 <small>글 1번의 글 주인은 멤버 1번입니다.</small>
 <small>글 2번의 글 주인은 멤버 2번입니다.</small>
 <small>글 3번의 글 주인은 멤버 3번입니다.</small>
 <img width="594" alt="image" src="https://user-images.githubusercontent.com/102157839/218021217-bc935714-fa2d-4900-a331-23d62c707c7e.png">
 <img width="280" alt="image" src="https://user-images.githubusercontent.com/102157839/218021337-f7cd877f-d80d-4f8d-8429-7018627d4a16.png">

<h3>구현 방식</h3>
  <h5>깃 커밋 컨벤션</h5>
      feat : 기능추가
      test : 테스트 코드 추가
      refactor : 기존코드 네이밍 변경 및 코드 분리
      
  <h5>패키지 구조</h5>
      전체적인 구조파악을 빠르게 할 수있고, 하나의 도메인이라 생각하여 레이어 계층 구조로 구현했습니다.
      
  <h5>구현 초점</h5>
      <strong>최대한 프론트 입장에서 받았을 때 처리가 편하게끔 목적을 두고 구현했습니다.</strong>
        1. 생성시간, 업데이트 시간을 프론트단에서 포맷할 필요 없이 공통된 포매터 객체를 통해 포맷된 형태로 response 해줍니다.
        2. 글 목록과 상세보기에서의 좋아요 체크는 회원일 경우 Y, N 으로 체크할 수 있고, 외부사용자인 경우에는 EX로
         response 해줍니다.
        3. 좋아요의 수를 비동기로 처리하기 위해 좋아요 요청을 보내면 증가된 좋아요의 수를 response 해줍니다.
        4. 조회수를 비동기로 처리하기 위해 상세보기 할 경우 증가된 조회수를 포함한 dto를 response 해줍니다.
      
      <strong>Header 체크와 request 유효성 체크</strong>
        1. Header체크는 필터를 통해 요청이 들어올 때 체크 후 dto로 변환하여 컨트롤단으로 전송해줍니다.
        2. reqeust 유효성은 AOP를 통해 체크하였습니다.
        
      <strong>예외 처리</strongSw>
        1. 발생할 수 있는 예외처리를 직접 핸들링 함으로써, 클라이언트단에서는 response의 code와 메세지를 통해 성공, 실패여부와 실패 시에 어느부분
           에서 오류가 발생했는지 쉽게 알 수 있게 했습니다.
           
  <h3>추가된 라이브러리</h3>
  httpclient : test시 RestTemplate의 PATCH 요청을 위해 추가했습니다.
  Swagger : 테스트도 할 수 있는 API 문서화를 위해 추가했습니다.
