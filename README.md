# Cats Workshop

## 함수형 프로그래밍

- 부수 효과가 없는 순수 함수로 코드를 만드는 것
- 부수 효과가 없는 프로그램은 아무 의미가 없다.
  - 어디선가 발생해야한다. 세상의 끝. 어디를 세상 끝으로 할 것인가!

## 예외를 이용하지 않은 오류 처리

- 예외의 단점
  - 함수형 프로그래밍에서 부수 효과
  - 참조 투명이 깨진다
- 예외의 장점
  - 예외를 던지는 연속된 코드가 있을 때 따로 처리하지 않아도 다음 코드를 실행시키지 않을 수 있다.
- 예외 대신 쓸 수 있는 함수형 툴
  - Option
  - Either
  - Monad

## IO를 순수하게 만들기

- IO를 실행하는 곳에서 IO를 실행하지 않고 IO를 실행할 것이라는 함수로 감싸고 나중에 실행한다.
- IO 형식의 장점
  - 형식적으로 참조 투명 (큰 가치는 없다)
  - IO를 실행하지 않은 상태에서 모나드 합성이 가능하다
  - 실행 방법을 결정하지 않았기 때문에 마지막에 비동기/동기 실행에 자유롭다. (어떻게와 언제를 분리)
- IO 형식의 단점
  - 절차적인 코드와 같이 함수적 합성이 불가능하다.
- IO 형식의 대안
  - 스트림 처리를 하면 IO도 함수적 합성이 가능하다. fs2 라이브러리
  
  
