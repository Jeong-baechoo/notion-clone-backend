# Notion Clone Project

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
