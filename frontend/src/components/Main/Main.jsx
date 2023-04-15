import React from "react";
import './main.scss'
import { Link } from "react-router-dom";

const Main = () => {

    return(
        <div className="main">
            <div className="container">
                <div className="main__content">
                    <div className="main__logo">useItStable</div>
                    <div className="main__title">приложение генерации синтетических данных для оптимизации процесса тестирования</div>
                    <Link className="main__link" to='/dbpage'>Войти</Link>
                </div>
            </div>
        </div>
    )
}

export default Main;