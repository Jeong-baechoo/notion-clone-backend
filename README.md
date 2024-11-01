# Notion Clone Project

## 노션 클론 구현 기능 명세

## 1. 사용자 관리 (User Domain)

### 1.1 인증

- 회원가입
- 로그인/로그아웃
- 소셜 로그인 (Google)

### 1.2 프로필

- 프로필 정보 수정

## 2. 워크스페이스 관리 (Workspace Domain)

### 2.1 워크스페이스 기본 기능

- 워크스페이스 CRUD
    - 생성, 조회, 수정(이름, 아이콘, 설명), 삭제

### 2.2 멤버 관리

- 멤버 초대/삭제
- 권한 관리 (Owner, Admin, Member)
- 초대 링크 생성/관리

## 3. 페이지 관리 (Page Domain)

### 3.1 페이지 기본 기능

- 페이지 CRUD
    - 최상위/하위 페이지 생성
    - 단일/목록 조회
    - 제목/내용 수정
    - 계층 구조를 포함한 삭제

### 3.2 페이지 구조 관리

- 페이지 이동 (drag & drop)
- 페이지 복사 (참조 관계 처리)
- 계층 구조 관리
- 사이드바 표시/제어

### 3.3 부가 기능

- 즐겨찾기 (추가/제거/순서관리)
- 최근 방문 페이지
- 검색 (제목/내용/워크스페이스별)

## 4. 에디터 관리 (Editor Domain)

### 4.1 BlockNote 에디터

- 에디터 데이터 관리 (JSONB)
- 자동 저장
- 페이지 참조 블록 처리

## 5. 공유 및 권한 관리 (Share Domain)

### 5.1 공유 설정

- 페이지 단위 공유
- 워크스페이스 단위 공유
- 공유 링크 생성

### 5.2 권한 관리

- 페이지별 접근 권한
- 워크스페이스별 권한

## 6. 협업 관리 (Collaboration Domain)

### 6.1 댓글과 토론

- 페이지 내 댓글 작성
- 특정 블록에 댓글 달기
- 댓글 스레드 관리
- 멘션(@) 기능

### 6.2 알림

- 페이지 변경 알림
- 댓글 알림
- 멘션 알림
- 초대 알림
- 알림 설정 관리

### 6.3 활동 이력

- 페이지별 활동 이력
- 워크스페이스 활동 이력
- 사용자별 활동 이력
- 필터링 및 검색

### 6.4 동시 작업 관리

- 편집 중인 페이지 잠금
- 충돌 방지
- 작업 상태 표시
- 변경 사항 병합


## 기술 스택

### Backend
- Java 21
- Spring Boot 3.2
- Spring Data JPA
- Spring Security
- JWT

### Database
- PostgreSQL
- H2 (테스트용)

## 구현된 기능

### 인증
- 회원가입
- 로그인/로그아웃
- JWT 기반 인증

### 워크스페이스
- 워크스페이스 생성
- 워크스페이스 조회/수정/삭제
- 멤버 초대 및 권한 관리
- 워크스페이스 상세 조회 (멤버 목록 포함)

## 프로젝트 구조
```
src/
├── main/
│   └── java/
│       └── com.example.notion/
│           ├── global/
│           │   ├── config/
│           │   ├── security/
│           │   │   ├── jwt/
│           │   │   └── util/
│           │   ├── exception/
│           │   └── common/
│           └── domain/
│               ├── user/
│               │   ├── controller/
│               │   ├── service/
│               │   ├── repository/
│               │   ├── entity/
│               │   └── dto/
│               │       ├── request/
│               │       └── response/
│               └── workspace/
│                   ├── controller/
│                   ├── service/
│                   ├── repository/
│                   ├── entity/
│                   └── dto/
│                       ├── request/
│                       └── response/
└── test/
    └── java/
        └── com.example.notion/
            └── domain/
                ├── user/
                │   └── service/
                └── workspace/
                    └── service/

```
## 현재 진행 중
- 단위 테스트 작성
- 워크스페이스 초대 수락 프로세스 구현

## 예정 사항
- 페이지 도메인 구현
- 실시간 협업 기능
