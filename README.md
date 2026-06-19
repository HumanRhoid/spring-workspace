# spring-workspace
🌱 김영한 스프링 입문 강의 기반 학습 저장소

## 📖 학습 목표
| 주제 | 상태 |
| :--- | :--- |
| 스프링 웹 개발 기초 (정적 / MVC / API) | ✅ 완료 |
| 회원 서비스 개발 (Controller → Service → Repository) | ✅ 완료 |
| 인메모리 리포지토리 구현 (`HashMap`) | ✅ 완료 |
| 테스트 코드 작성 (`JUnit5`, given-when-then) | ✅ 완료 |
| 스프링 빈 등록 (`@Service` / `@Repository` / `@Controller`) | ✅ 완료 |
| 생성자 의존성 주입 (Constructor DI) | ✅ 완료 |
| 패키지 구조 — 레이어별 → **도메인별** 리팩토링 | ✅ 완료 |
| JPA / 스프링 데이터 JPA 연동 | ⬜ 예정 |
| AOP | ⬜ 예정 |

---

## 🗂️ 프로젝트 구조

```
src/main/java/com/example/
├── MyApplication.java          # 부트 진입점
├── member/                     # 회원 도메인
│   ├── Member.java             # 도메인 객체
│   ├── MemberRepository.java   # 리포지토리 인터페이스
│   ├── MemoryMemberRepository  # 인메모리 구현체 (HashMap)
│   ├── MemberService.java      # 비즈니스 로직
│   ├── MemberForm.java         # 폼 DTO
│   └── MemberController.java   # HTTP 요청 처리
└── hello/                      # 웹 개발 기초 예제
    └── HelloController.java    # 정적 / MVC / API 예제
```

---

## ⚙️ 기술 스택
| 분류 | 내용 |
| :--- | :--- |
| **언어** | Java 21 |
| **프레임워크** | Spring Boot 3.4.1 |
| **빌드** | Gradle |
| **뷰** | Thymeleaf |
| **저장소** | In-Memory (HashMap) → JPA 전환 예정 |
| **테스트** | JUnit 5, AssertJ |

---

## 🚀 실행 방법

```bash
./gradlew bootRun
# → http://localhost:8080
```

| URL | 설명 |
| :--- | :--- |
| `GET /` | 홈 (회원 가입 / 목록 링크) |
| `GET /members/new` | 회원 가입 폼 |
| `POST /members/new` | 회원 등록 |
| `GET /members` | 회원 목록 |
| `GET /hello` | Thymeleaf 예제 |
| `GET /hello-mvc?name=이름` | MVC 예제 |
| `GET /hello-api?name=이름` | JSON API 예제 |

---

## 🏗️ 요청 흐름

```
Browser
  └─ HTTP 요청
       └─ DispatcherServlet
            └─ MemberController        @Controller
                 └─ MemberService      @Service
                      └─ MemoryMemberRepository  @Repository
                           └─ HashMap (인메모리 저장소)
```

---

## 🎯 Git Commit Convention

| 이모지 | 타입 | 설명 |
| :--- | :--- | :--- |
| ✨ | **Feat** | 새로운 기능 추가 |
| 🐛 | **Fix** | 버그 수정 |
| ♻️ | **Refactor** | 코드 구조 개선 |
| 🔧 | **Chore** | 빌드 설정, 의존성 수정 |
| 📝 | **Docs** | 문서 수정 |

> **형식:** `이모지 타입: 작업 내용 요약`
> - 예: `✨ Feat: 회원 가입 기능 구현`
> - 예: `♻️ Refactor: 패키지 구조 도메인별로 전환`
