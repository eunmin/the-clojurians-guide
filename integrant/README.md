# Integrant 배우기

## Integrant는 어떤 라이브러리 인가요?

Integrant는 상태 관리 라이브러리 입니다. 상태 관리가 뭔지 예제를 통해 알아봅시다.

아래는 `clojure.java.jdbc` 라이브러리로 데이터베이스에 질의 하는 예제 코드 입니다.

```clojure
(require '[clojure.java.jdbc :as j])

(defn find-all-fruit-by-appearance [db appearance]
  (j/query db ["select * from fruit where appearance = ?" appearance]))
```

## 따라해보는 간단한 예제

## 설정

## 시작과 종료

## 일시 정지와 다시 시작하기

## 설정 초기화

## 키워드 상속

## 복합키

## 복합키 참조

## 스펙

## 네임스페이스 로드하기
