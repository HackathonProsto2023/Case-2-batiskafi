import './contacts.scss'

function Contacts() {
  return (
    <div className="main">
      <div className="contacts">
        <div className="contacts__grid">
          <div className="contacts__grid-item">
            <div className="contacts__grid-avatar">

            </div>
            <div className="contacts__grid-text">
              <div className="contacts__grid-title">Frontend</div>
              <div className="contacts__grid-name">Стрельников Максим</div>
              <a className="contacts__grid-git" href="https://github.com/MaksSTV">Github</a>
            </div>
          </div>
          <div className="contacts__grid-item">
            <div className="contacts__grid-avatar">

            </div>
            <div className="contacts__grid-text">
              <div className="contacts__grid-title">Backend/DevOps</div>
              <div className="contacts__grid-name">Блюдин Андрей</div>
              <a className="contacts__grid-git" href="https://github.com/Andrey529">Github</a>
            </div>
          </div>
          <div className="contacts__grid-item">
            <div className="contacts__grid-avatar">

            </div>
            <div className="contacts__grid-text">
              <div className="contacts__grid-title">Backend/PM</div>
              <div className="contacts__grid-name">Мазин Сергей</div>
              <a className="contacts__grid-git" href="https://github.com/imserega">Github</a>
            </div>
          </div>
          <div className="contacts__grid-item">
            <div className="contacts__grid-avatar">

            </div>
            <div className="contacts__grid-text">
              <div className="contacts__grid-title">DataBase</div>
              <div className="contacts__grid-name">Горюнов Дмитрий</div>
              <a className="contacts__grid-git" href="https://github.com/mindvorg">Github</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Contacts;