const data = [
    { 
        "tableName": "position",
        "entryCount": 0,
        "columns":[{"positionid":0}, {"positionname":""}],
        "references":[]
    }, 
    {
        "tableName": "jobhistory",
        "entryCount": 0,
        "columns":[{"jobhistoryid": 0}, {"lengthofwork": 0}, {"positionid": 0 }, {"companyname": ''}],
        "references":["position"]
    }, 
    { 
        "tableName": "project",
        "entryCount": 0,
        "columns":[{"projectid": 0}, {"projectname": ''}, {"description": '' }],
        "references":[]
    }, 
    { 
        "tableName": "projectskill",
        "entryCount": 0,
        "columns":[{"projectskillid": 0}, {"projectid": 0}, {"skillid": 0 }],
        "references":["project","skill"]
    }, 
    { 
        "tableName": "skill",
        "entryCount": 0,
        "columns":[{"skillid":0}, {"skillname":""}],
        "references":[]
    }, 
    { 
        "tableName": "skill",
        "entryCount": 0,
        "columns":[{"employeeid": 0}, {"positionid": 0}, 
                    {"salary": 0 }, {"lengthofwork": 0},
                    {"firstname": '' }, {"lastname": ''},
                    {"levelname": '' }],
        "references":["jobhistory","position","project"]
    }]