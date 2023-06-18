pipeline{

    agent any

    stages{

        stage("Build"){
            steps{
                echo("Building the project")
            }
        }

        stage("Run UTs"){
            steps{
                echo("Running unit tests")
            }
        }


        stage("Deploy to dev"){
            steps{
                echo("Deploying to dev env")
            }
        }



        stage("Deploy to qa"){
            steps{
                echo("Deploying to qa env")
            }
        }

        stage("Run regression automation test cases"){
            steps{
                echo("Running regression automation test cases")
            }
        }

        stage("Deploy to stage"){
            steps{
                echo("Deploying to stage env")
            }
        }

        stage("Run sanity automation test cases"){
            steps{
                echo("Running sanity automation test cases")
            }
        }

        stage("Deploy to prod"){
            steps{
                echo("Deploying to prod env")
            }
        }
    }
}