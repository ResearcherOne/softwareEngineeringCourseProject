Software Architecture
	routes.js
		GET
			/toptenlist.json
				[{"username": "birkan", "score":500, "date":1231231231}]
		POST
			/postscore
				username gameScore clientId
	mongoModule.js
		saveScore()
			isClientIdExist
		getTopTenList()
		clearAllScores()
	utility.js
		getCurrentDate
		
Tests
	Integration Test
	Unit Test