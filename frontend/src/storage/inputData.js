export const inputData = [
    {
        tableName: 'skill',
        columnName: ['skillid', 'skillname'],
        dataType: ['integer', 'character varying'],
        constraintType: ['PK', ''],
        references: [],
    },
    {
        tableName: 'projectskill',
        columnName: ['projectskillid', 'projectid', 'skillid'],
        dataType: ['integer', 'integer', 'integer'],
        constraintType: ['PK', 'FK', 'FK'],
        //references: ['', 'project.projectid', 'skill.skillid']
        references: ['skill', 'project'],
    },
    {
        tableName: 'project',
        columnName: ['projectid', 'projectname', 'description'],
        dataType: ['integer', 'character varying', 'character varying'],
        constraintType: ['PK', '', ''],
        references: [],
    },
    {
        tableName: 'employee',
        columnName: ['employeeid', 'firstname', 'lastname', 'salary', 'levelname', 'lengthofwork', 'positionid', 'projectid', 'jobhistoryid'],
        dataType: ['integer', 'character varying', 'character varying', 'integer', 'character varying', 'integer', 'integer', 'integer', 'integer'],
        constraintType: ['PK', '', '', '', '', '', 'FK', 'FK', 'FK'],
        references: ['position', 'project', 'jobhistory'],
    },
    {
        tableName: 'position',
        columnName: ['positionid', 'positionname'],
        dataType: ['integer', 'character varying'],
        constraintType: ['PK', '', ''],
        references: [],
    },
    {
        tableName: 'jobhistory',
        columnName: ['jobhistoryid', 'companyname', 'lengthofwork', 'positionid'],
        dataType: ['integer', 'integer', 'integer', 'character varying'],
        constraintType: ['PK', '', '', 'FK'],
        references: ['position'],
    }
]