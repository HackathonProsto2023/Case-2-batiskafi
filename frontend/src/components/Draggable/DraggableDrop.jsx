import React, { useState } from "react";
import Draggable from 'react-draggable';
import { useXarrow } from "react-xarrows";
import mapOfData from "../../store/mapOfData";
import '../Draggable/draggable.scss';
import MakeList from "../MakeList/MakeList";
import Modal from "../Modal/Modal";
import { data } from "../../storage/localStorage";


const Input = ({ typeData, onChange, name, typeKey }) => {

  const [value, setValue] = useState('');

  const change = (e) => {
    setValue(e.target.value);
    if (select === 'number') {
      onChange(+e.target.value);
    }
    else
      onChange(e.target.value);
  }

  //console.log(type)

  let select = 'text'

  if (typeData === 'integer') {
    select = 'number'
  }


  return (
    <div className="MyInput">
      <label>{name}</label>
      <input
      disabled={false}
      className="modal__input"
      type={select}
      value={value}
      placeholder="Поле для ввода"
      onChange={change}
    />
    </div>
    
  );
}

const DraggableBox = ({ box }) => {
  const updateXarrow = useXarrow();
  const [modalActive, setModalActive] = useState(true)

  const [entryCount, setEntryCount] = useState(0)

  //console.log(box)

  const changeEntry = (e) => {
    setEntryCount(e.target.value);
  }

  /**{ 
        "tableName": "position",
        "entryCount": 0,
        "columns":[
            { 
                "columnName" : "positionid",
                "rule" : "...",
                "key" : "PK",
                "reference" : ""
            }, */

  function saveChange(){

    const obj = {
      tableName: String,
      entryCount: Number,
      columns: [
      ]
    }

    obj.tableName = box.tableName
    obj.entryCount = +entryCount
    /*
    obj.columns.push({columnName: '123', rule: "123", key:"123", reference: "213"})
    obj.columns.push({columnName: '123', rule: "123", key:"123", reference: "213"})
    obj.columns.push({columnName: '123', rule: "123", key:"123", reference: "213"})*/
    console.log(box)


    for(let i = 0; i < box.columnName.length; i++){

      let col = box.columnName[i]
      let rule = mapOfData.map1.get(col)
      let key = box.constraintType[i]
      let ref = 'table.field'


      obj.columns.push({
        columnName: col, 
        rule: rule, 
        key: key, 
        reference: ref
      })
    }

     /* data.forEach((val)=>{
        if(box.tableName === val.tableName){
          val.entryCount = +entryCount
        }
        val.columns.forEach(col => {
          for(let key in col){
            col[key] = mapOfData.map1.get(key)
          }
        })
      })
      
      console.log(data)
      console.log(mapOfData)*/

      console.log(obj)
  }


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
                key={index}
                onChange={(v) => mapOfData.addMap1(val, v)}
                typeData={box.dataType[index]}
                typeKey={box.constraintType[index]}
                name={val}
              />
            ))

          }
          <div className="MyInput">
            <label>Количество записей</label>
            <input
              name="counter"
              className="modal__input"
              type="number"
              value={entryCount}
              placeholder="Количество записей"
              onChange={changeEntry}
            />
          </div>
          <button className="modal__btn" type="submit" onClick={saveChange}>Сохранить</button>
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