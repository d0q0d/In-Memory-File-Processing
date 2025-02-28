# Running the Application

You can use the provided Dockerfile to build and run the application. The application does not rely on any external database or services as it processes the IMDB dataset locally in memory.

Steps to Run the Application:
1.	Download the IMDB Dataset
To test the application you need to download the following files from the IMDB Interfaces page:
•	name.basics.tsv
•	title.basics.tsv
•	title.crew.tsv
•	title.ratings.tsv
2.	Place the Dataset Files
After downloading the files place them in a single directory on your system. This directory will be used to load the files into the application.
3. Using the API
Once the container is running you can interact with the application via its exposed REST APIs. The application will load the IMDB dataset into memory and process it as per your API calls