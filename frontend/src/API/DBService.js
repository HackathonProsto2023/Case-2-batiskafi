import axios from "axios";

export default class DBService{
    static async getDB(){
        try{
            const response = await axios.get('http://194.58.108.56:8080/api/dbase')
            return response.data
        }
        catch(e){
            console.log(e)
        }
    }
}