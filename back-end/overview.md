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
		initializeModule()
	utility.js
		getCurrentDate
		
Tests
	Integration Test
	Unit Test
	
Data Structure
	scoresCollection
		{"username": "birkan", "score":500, "date":1231231231, "client_id": 5124123123}
		{"username": "birkan", "score":500, "date":1231231231}
		{"username": "birkan", "score":500, "date":1231231231}