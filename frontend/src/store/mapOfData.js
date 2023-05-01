import { makeAutoObservable } from "mobx";


class MapOfData{

    map1 = new Map()

    constructor(){
        makeAutoObservable(this)
    }

    addMap1(key, value){
        this.map1.set(key, value)
    }
}

export default new MapOfData()