import React, { useEffect, useState } from "react";
import Draggable from 'react-draggable';
import { useXarrow } from "react-xarrows";
import MakeList from "../MakeList/MakeList";
import '../Draggable/draggable.scss'
import Modal from "../Modal/Modal";




const Input = ({ onChange }) => {

  const [value, setValue] = useState('');

  const change = (e) => {
    setValue(e.target.value);
    onChange(e.target.value);
  }

  return (
    <input 
      className="modal__input" 
      type="text" 
      value={value}
      placeholder="Поле для ввода" 
      onChange={change}
    />
  );
}

const DraggableBox = ({ box }) => {
  const updateXarrow = useXarrow();
  const [modalActive, setModalActive] = useState(true)


  return (
    <div>
      <Draggable onDrag={updateXarrow} onStop={updateXarrow}>
        <div id={box.tableName} className="box" onDoubleClick={() => setModalActive(false)}>
          <div className="box__title">{box.tableName}</div>
          <div className="box__content">
            <div className="box__content-name">
              <MakeList array={box.columnName} />
            </div>
            <div className="box__content-datatype">
              <MakeList array={box.dataType} />
            </div>
            <div className="box__content-constraint">
              <MakeList array={box.constraintType} /></div>
          </div>
        </div>

      </Draggable>
      <Modal active={modalActive} setActive={setModalActive}>
        <div className="table__title">
          {box.tableName}
        </div>
        <form className="modal__form" action="submit" onSubmit={
          (e) => {
            e.preventDefault()
          }
        }>
          
          {
            
            box.columnName.map((val, index) => (
              <Input
                
                /*onMount={() => setArr('')}*/
                /*onChange={(v) => setSet(set.add(v))}*/
                key={index}
              />
            ))

          }
          <input name="counter" className="modal__input" type="number" placeholder="Количество записей"/>
          <button className="modal__btn" type="submit">Сохранить</button>
        </form>
      </Modal>
    </div>

  );
};


const DraggableDrop = ({ tables }) => {

  return (
    <div>
      {
        tables.map((table, index) =>
          <div key={index + 1}>
            <DraggableBox box={table} key={index} />
          </div>
        )
      }
    </div>
  )
}

export default DraggableDrop;