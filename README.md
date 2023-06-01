# SportsQuiz

Приложение-игра Спортивная викторина, где надо отвечать на вопросы на время. За правильные ответы начисляются очки, которые можно потратить в магазине обоев, чтобы поставить заставку на рабочий стол.

Приложение построено на принципах Clean архитектуры с паттерном MVVM.

Вопросы хранятся локально в БД, а если она затирается, например при переустановке приложения, снова подгружаются из внутреннего класса

Обои подгружаются из сети, сделал для этого общедоступный json в интернете (https://api.jsonbin.io/v3/b/6477aa6a8e4aa6225ea74e56)

Очки и информация о том что те или иные обои куплены хранится Shared Preferences

Состоит из 9 модулей:

  ~app - модуль ядро, тянет на себе и раздаёт основные DI в DI feature-модулей которые нужны в единственном числе на всё приложение
  
  ~main-screen-ui - feature-модуль с главным экраном где есть кнопки перехода в игру и в магазин и отображается баланс очков
  
  ~game-screen-ui - feature-модуль с выбором уровней сложности и самой викториной
  
  ~wallpaper-screen-ui - feature-модуль с магазином обоев с функционалом покупки обоев и установки их на заставку в телефоне
  
  ~main-screen-domain, game-screen-domain, wallpaper-screen-domain - модули с бизнес логикой, внутри юзкейсы объединенные в интерактор (по одному на каждый модуль), посредники между data слоем и presentation слоем
  
  ~data - содержит доступ к БД, работе с сетью, внутренней памяти телефона
  
  ~common - модуль с классами которые используются больше чем в одном модуле, также содержит в себе strings и dimensions