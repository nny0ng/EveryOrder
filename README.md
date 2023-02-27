# 시각 장애인 및 시니어 계층과 함께 사용하는 태블릿 메뉴판, EveryOrder
#### 🥈 2022년도 컴퓨터학부 소프트웨어공모전 은상🥈<br><br>
![main image](https://user-images.githubusercontent.com/69834230/221628882-3c44091e-e71f-4962-b1da-46f5727fcc49.png) <br><br>

## **🔎 출품 분야**
- 모바일 SW <br><br>


## **🔎 기획 의도**  
키오스크, 태블릿 메뉴판 등의 비대면 주문 시스템은 해마다 급증하고 있다. 인건비 감소의 목적, 코로나19로 인한 비대면 서비스 확대, 서비스직의 문제점 등을 이유로 몇 년 사이 우리나라의 주요한 결제 수단으로 빠르게 자리 잡은 것이다. 그러나 이는 모든 사용자가 이용할 수 없어 많은 사회적 문제를 야기한다.

 기존의 비대면 주문 시스템은 눈으로 구동 방법을 파악, 메뉴 선정 후 터치를 통해 주문하는 방식으로 구체적인 주문 방법을 따로 명시하지 않는다. 이에 시행착오나 경험을 통해 주문하는 방법을 배워야 한다는 단점이 있으며, 해당 단점은 짧은 시간 내에 정보를 습득하는 것이 익숙지 않은 시니어 계층과 시각적 경험이 불가한 시각 장애인에게 높은 진입장벽을 주는 요인이다.
결국, 시니어 계층과 시각 장애인은 비대면 주문 시스템을 도입한 매장을 이용하는 것이 꺼려질 수밖에 없다. 비대면 주문 시스템의 도입으로 사용 가능한 매장이 줄어들수록 해당 계층의 사람들이 소외당하는 영역도 점차 증가하게 될 것이다.

 비대면 주문 결제가 대중적인 방식으로 자리잡힌 이후, 해당 방식의 문제점을 해결하기 위한 움직임이 많아졌지만, 눈에 띄게 효과적인 것은 아직 찾아볼 수 없다. 또한, 키오스크의 문제점에 반하는 발전 동향은 활발하지만, 태블릿 메뉴판의 동향은 찾아볼 수 없었다. 이에 비대면 주문 시스템의 문제점을 해결한 태블릿 메뉴판 앱인 <EveryOrder>를 개발하였다.

 <EveryOrder>는 기존 터치 방식은 유지하되, 음성 안내(TTS)와 음성 인식(STT)을 이용하여 시각 장애인 및 시니어 계층 모두 비대면 주문 결제를 이용할 수 있는 기능을 제공한다. 음성 안내(TTS) 기능은 주문방식에 대한 정보를 손쉽게 습득해 원활한 주문이 가능하도록, 음성 인식(STT) 기능은 시니어 계층에게는 익숙한, 시각 장애인에게는 편의를 위한 음성 주문이 가능하게 한다. <br><br>


## **🔎 작품 설명**
### i) 개발 목표
> 본 프로젝트는 음성 안내(TTS), 음성 인식(STT) 기술을 기존의 터치 방식과 결합하여 시각 장애인 및 시니어 계층을 포함한 모든 사람을 사용 대상으로 하는 태블릿 메뉴판 <EveryOrder>를 개발하는 것을 목표로 한다.
### ii) 개발 개요
- 기본적으로 일반 사용자가 사용할 수 있는 앱을 터치 방식으로 구현한다.
-  시각 장애인의 사용을 위해 음성 안내(TTS), 음성 인식(STT)만을 이용해 앱이 동작할 수 있도록 구현한다.
- 시니어 계층의 사용은 터치와 음성 안내(TTS), 음성 인식(STT) 중 원하는 방식을 언제든 사용할 수 있도록 앱을 구현한다. <br><br>


## **🔎 시스템 구성도**

![system structure map](https://user-images.githubusercontent.com/69834230/221624162-561138af-f4b7-4776-81d9-23dfc100f700.png) <br>
> 회색 박스는 공통 화면, 노란색 박스는 터치 버전 기능, 파란색 박스는 음성 버전 기능을 의미한다. <br>

- 해당 앱은 터치+음성방식으로 동작하므로 공통 화면에서 터치와 음성 부분으로 기능이 나뉜다. 처음 <시작 화면>에서 터치/음성을 이용해 <메인 화면>으로 넘어가게 된다.
- <메인 화면>은 크게 메뉴, 장바구니 기능이 있다.
터치 이용자는 메뉴를 선택하거나 +, - 버튼을 이용하여 주문할 상품을 장바구니에 넣을 수 있다. 이후 주문하기 버튼을 누르면 <주문 화면>으로 넘어간다.
음성 이용자는 음성안내에 따라 메뉴를 들을 수 있고, 주문할 상품을 말해 장바구니에 상품을 넣을 수 있다. 더 넣을 것이 없다면 음성 안내에 따라 <주문 화면>으로 넘어갈 수 있다.
- <주문 화면>은 주문을 확인하고 확정한다. 터치 이용자는 주문 확정 버튼을 통해, 음성 이용자는 음성 안내에 따라 주문을 완료할 수 있다. 주문 완료 후에는 처음 <시작 화면>으로 돌아간다.
- <호출 화면>은 터치 이용자가 호출 버튼을 누르거나 음성 이용자가 “호출”이라고 말할 때 이동된다. 터치 이용자는 호출 옵션에 따라 이용할 수 있고 음성 이용자는 자동으로 직원을 호출해준다. 호출이 끝나면 이전 화면으로 돌아간다. <br><br>


## **🔎 소프트웨어 흐름도**
### i) 시각 장애인 및 시니어 계층 관점의 흐름도
![flowchart1](https://user-images.githubusercontent.com/69834230/221627583-2f8b9cb0-4dcc-4f32-8bca-301d89c8f52a.png) <br>

### ii) 일반 사용자 관점의 흐름도
![flowchart2](https://user-images.githubusercontent.com/69834230/221627844-0bff85c3-6eef-4805-b843-f55eb5ab510f.png) <br><br>

## **🔎 개발 환경**
|||
|:------:|:--------:|
|**OS**|Window|
|**개발 언어**|Java|
|**개발 툴**|Android Studio (Google TTS, STT API 사용)|
|**대상 기기**|Android 태블릿|
|**테스트 기기**|갤럭시 탭 S8 (SM-X700)|
