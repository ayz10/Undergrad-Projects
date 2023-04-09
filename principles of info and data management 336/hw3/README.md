## Data Generation

To create the random 100 student names and classes I used online tools so as not to create them all by hand. I then used a developer tool called Alteryx too create random relationships between the students and classes, as well as assigning different credit values to each class. Every student is assigned at least 1 major and 1 minor, with about 25% of students having a double major and 10% having a double minor. These were all saved as text files which are passed into my 'db_setup.java' class. db_setup.java parses the text files and inserts the data into the database.

