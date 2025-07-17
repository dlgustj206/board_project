# 📝 게시판 프로젝트

## ⚙️ 개발 환경

| 항목           | 버전 및 정보                  |
|----------------|-------------------------------|
| 💻 IDE         | IntelliJ IDEA Community       |
| ☕ JDK         | JDK 21                        |
| ⚡ Framework   | Spring Boot 3.5.3             |
| 🗄️ DB         | MySQL 8.0.42                   |
| 🔗 ORM         | Spring Data JPA               |
| 🎨 Template   | Thymeleaf                     |

---

## 🚀 주요 기능

1.  **글 작성**
    - `/board/save`
    - 제목, 작성자, 내용 입력 가능
    - 단일/다중 파일 첨부 가능

2. 📋 **글 목록 조회**
    - `/board/`

3.  **글 상세 조회**
    - ``/board/{id}`
    - 게시글 정보, 첨부파일, 댓글 목록 표시

4.  **글 수정**
    - `/board/update/{id}`
    - 상세 화면에서 수정 버튼 클릭 → 수정 화면 출력 → 제목/내용 수정 후 요청 → 처리
    - 제목, 내용 수정 가능
    - 기존 첨부파일 삭제 및 새 파일 추가 가능

5.  **글 삭제**
    - `/board/delete/{id}`
    - 해당 게시글과 첨부파일, 댓글 일괄 삭제

6.  **페이징 처리**
    - `/board/paging?page=2` 또는 `/board/paging/2`
    - 한 페이지당 5개 게시글 표시
    - (예: 총 14개 → 총 3페이지)

7.  **파일 첨부 및 삭제**
    - 글 작성/수정 시 단일 또는 다중 파일 첨부 가능
    - 수정 화면에서 파일 추가 및 삭제 가능

8. 댓글 기능 
    - 댓글 작성자 / 내용 입력 후 등록
    - 게시글 하단에 실시간 표시

---

## 📂 프로젝트 폴더 구조

```plaintext
📦 src
 ┗━ 📂 main
     ┣━ 📂 java/com/board
     ┃   ┣━ 📂 config
     ┃   ┃   ┗━ WebConfig.java
     ┃   ┣━ 📂 controller
     ┃   ┃   ┣━ BoardController.java
     ┃   ┃   ┣━ CommentController.java
     ┃   ┃   ┗━ HomeController.java
     ┃   ┣━ 📂 dto
     ┃   ┃   ┣━ BoardDTO.java
     ┃   ┃   ┣━ BoardFileDTO.java
     ┃   ┃   ┗━ CommentDTO.java
     ┃   ┣━ 📂 entity
     ┃   ┃   ┣━ BaseEntity.java
     ┃   ┃   ┣━ BoardEntity.java
     ┃   ┃   ┣━ BoardFileEntity.java
     ┃   ┃   ┗━ CommentEntity.java
     ┃   ┣━ 📂 repository
     ┃   ┃   ┣━ BoardRepository.java
     ┃   ┃   ┣━ BoardFileRepository.java
     ┃   ┃   ┗━ CommentRepository.java
     ┃   ┣━ 📂 service
     ┃   ┃   ┣━ BoardService.java
     ┃   ┃   ┗━ CommentService.java
     ┃   ┗━ BoardApplication.java
     ┃
     ┗━ 📂 resources
         ┣━ 📂 templates
         ┃   ┣━ detail.html
         ┃   ┣━ index.html
         ┃   ┣━ list.html
         ┃   ┣━ paging.html
         ┃   ┣━ save.html
         ┃   ┗━ update.html
         ┣━ application.yml
         ┗━ .env
```

---

## 📑 .env 파일 예시

```env
DB_URL=jdbc:mysql://localhost:3306/board_db
DB_USERNAME=root
DB_PASSWORD=your_password
```

---

## 🗄️ 데이터베이스 테이블 스키마

```sql
-- 게시판 테이블
CREATE TABLE board
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_time   DATETIME NULL,
    updated_time   DATETIME NULL,
    board_contents VARCHAR(500) NULL,
    board_hits     INT NULL,
    board_pass     VARCHAR(255) NULL,
    board_title    VARCHAR(255) NULL,
    board_writer   VARCHAR(20) NOT NULL,
    file_attached  INT NULL
);

-- 첨부파일 테이블
CREATE TABLE board_file
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    original_file_name VARCHAR(255) NULL,
    stored_file_name   VARCHAR(255) NULL,
    board_id           BIGINT NULL,
    created_time       DATETIME NULL,
    updated_time       DATETIME NULL,
    CONSTRAINT fk_board_file
        FOREIGN KEY (board_id) REFERENCES board(id)
        ON DELETE CASCADE
);

-- 댓글 테이블
CREATE TABLE comment
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    comment_writer VARCHAR(20) NOT NULL,
    comment_contents TEXT,
    board_id BIGINT,
    created_time DATETIME,
    updated_time DATETIME,
    CONSTRAINT fk_board
        FOREIGN KEY (board_id) REFERENCES board(id)
        ON DELETE CASCADE
);
