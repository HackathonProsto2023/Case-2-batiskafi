import './dbpage.scss'
import React, { useEffect, useState } from 'react';
import Xarrow, { Xwrapper, useXarrow } from 'react-xarrows';
import DBService from '../../API/DBService';
import DraggableDrop from '../../components/Draggable/DraggableDrop';
import Arrow from '../../components/Arrow/Arrow';
import DataBaseAsideList from '../../components/DataBaseAsideList/DataBaseAsideList';
import {inputData} from '../../storage/inputData'



function DBPage() {

  const [tables, setTables] = useState([])

  useEffect(()=>{
    //getData()
    setTables(inputData)
  }, [])

  async function getData(){
    const response = await DBService.getDB();
    setTables(response)
  }

  return (
    <div className="main" >
      <div className="container">
        <div className="database">
          <div className="database__content">
            <div className="database__aside">
              <DataBaseAsideList tables={tables}/>
            </div>
            <div className="database__render">
              <div className="database__canvas">
                <div className='canvasStyle' id="canvas">
                  <Xwrapper> 
                    <DraggableDrop tables={tables}/>
                    <Arrow tables={tables}/>
                  </Xwrapper>
                </div>
              </div>
              <div className="database__btns">
                <button className="btn__generate">Сгенерировать</button>
                <button className="btn__appload">Скачать</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default DBPage;