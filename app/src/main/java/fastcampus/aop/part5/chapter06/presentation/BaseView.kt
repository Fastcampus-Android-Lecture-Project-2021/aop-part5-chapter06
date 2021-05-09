package fastcampus.aop.part5.chapter06.presentation

interface BaseView<PresenterT : BasePresenter> {

    val presenter: PresenterT
}
