# 📌 Schedule API 명세서

## 🔗 Base URL
/api/schedules

---

## ✅ 1. 일정 생성
- **Method**: `POST`
- **URL**: `/api/schedules`
- **설명**: 새로운 일정을 생성합니다.

- **Request Body**:
```json
{
  "name": "홍길동",
  "password": "1234",
  "todo": "보고서 작성"
}
```

- **Response Body**:
```json
{
  "id": 1,
  "name": "홍길동",
  "todo": "보고서 작성",
  "createdDate": "2025-05-25T00:00:00",
  "updatedDate": "2025-05-25T00:00:00"
}
```

- **상태코드**:
  - `200 OK`: 정상 생성
  - `400 Bad Request`: 요청 데이터 누락

---
## ✅ 2. 전체 일정 조회
- **Method**: `GET`
- **URL**: `/api/schedules`
- **설명**: 등록된 일정을 조회합니다. `name`, `updatedDate` 기준으로 조건 검색이 가능합니다.

- **Query Parameters**:
  - name (선택): 작성자명으로 필터링
  - updatedDate (선택): 수정일 **이후** 일정으로 필터링 (형식: YYYY-MM-DD)

- **Response Body**:
``` json
[
  {
    "id": 1,
    "name": "홍길동",
    "todo": "보고서 작성",
    "createdDate": "2025-05-25T00:00:00",
    "updatedDate": "2025-05-25T00:00:00"
  },
  {
    "id": 2,
    "name": "김영희",
    "todo": "회의 준비",
    "createdDate": "2025-05-24T15:00:00",
    "updatedDate": "2025-05-25T09:00:00"
  }
]
```

- **상태코드**:
  - `200 OK`: 정상 조회

---
## ✅ 3. 선택 일정 조회
- **Method**: `GET`
- **URL**: `/api/schedules/{id}`
- **설명**: 특정 id의 일정을 조회합니다.

- **Query Parameters**:
    - id: 조회할 일정의 ID

- **Response Body**:
``` json
{
  "id": 1,
  "name": "홍길동",
  "todo": "보고서 작성",
  "createdDate": "2025-05-25T00:00:00",
  "updatedDate": "2025-05-25T00:00:00"
}
```

- **상태코드**:
  - `200 OK`: 정상 조회
  - `404 Not Found`: 해당 id에 대한 일정이 존재하지 않음

---
## ✅ 4. 선택 일정 수정
- **Method**: `PATCH`
- **URL**: `/api/schedules/{id}`
- **설명**: id에 해당하는 일정을 수정합니다. name과 todo만 수정 가능하며, password는 검증용으로 반드시 필요합니다.

- **Query Parameters**:
  - id: 수정할 일정의 ID

- **Request Body**:

```json
{
  "name": "김철수",
  "password": "1234",
  "todo": "회의 장소 예약"
}
```

- **Response Body**:
```json
{
  "id": 1,
  "name": "김철수",
  "todo": "회의 장소 예약",
  "createdDate": "2025-05-25T00:00:00",
  "updatedDate": "2025-05-25T10:00:00"
}
```

- **상태코드**:
  - `200 OK`: 정상 수정 (작성자만, 할 일만, 둘 다 수정)
  - `400 Bad Request`: 요청 데이터 누락 또는 비밀번호 불일치
  - `404 Not Found`: 해당 id에 대한 일정이 존재하지 않음

---
## ✅ 5. 선택 일정 삭제
- **Method**: `DELETE`
- **URL**: `/api/schedules/{id}`
- **설명**: id에 해당하는 일정을 삭제합니다. password는 필수로 전달되어야 합니다.

- **Query Parameters**:
    - id: 삭제할 일정의 ID

- **Request Body**:
```json
{
  "password": "1234"
}
```

- **상태코드**:
    - `200 OK`: 정상 삭제
    - `400 Bad Request`: 요청 데이터 누락 또는 비밀번호 불일치
    - `404 Not Found`: 해당 id에 대한 일정이 존재하지 않음


## 📝 비고
모든 날짜 형식은 ISO 8601 (YYYY-MM-DDTHH:mm:ss) 기준입니다.

---
## 🗂️ Schedule 테이블 구조 (ERD)

| 필드명        | 타입         | 제약조건                      | 설명         |
|---------------|--------------|-------------------------------|--------------|
| id            | INT          | PK, AUTO_INCREMENT            | 일정 ID      |
| name          | VARCHAR(255) | NOT NULL                      | 작성자명      |
| password      | VARCHAR(255) | NOT NULL                      | 비밀번호      |
| todo          | VARCHAR(255) | NOT NULL                      | 할 일         |
| createdDate   | TIMESTAMP    | DEFAULT CURRENT_TIMESTAMP     | 작성일        |
| updatedDate   | TIMESTAMP    | DEFAULT CURRENT_TIMESTAMP<br>ON UPDATE CURRENT_TIMESTAMP | 수정일(자동 갱신) |

---
