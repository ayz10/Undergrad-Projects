#Alexander Zhao - ayz10

# --- PART 1: READING DATA ---

# 1.1
def read_ratings_data(f):
    lines = []
    movie_ratings_dict = {}
    with open (f, "r") as file:
        lines = file.readlines()
    file.close()

    for i in range(len(lines)):
        temp = lines[i][:-1].split("|")
        lines[i] = [temp[0], float(temp[1])]

    for i in lines:
        if i[0] not in movie_ratings_dict:
            movie_ratings_dict [i[0]] =[]
        movie_ratings_dict [i[0]].append(i[1])

    return movie_ratings_dict

# 1.2
def read_movie_genre(f):
    lines = []
    movie_genre_dict = {}
    with open (f, "r") as file:
        lines = file.readlines()
    file.close()

    for i in range(len(lines)):
        lines[i] = lines[i][:-1].split("|")
    
    for i in lines:
        movie_genre_dict[i[2]] = i[0]

    return movie_genre_dict

# --- PART 2: PROCESSING DATA ---

# 2.1
def create_genre_dict(d):
    genre_dict = {}
    for i in d.keys():
        temp_key = d[i]
        if not temp_key in genre_dict.keys():
            genre_dict[temp_key] = []
        genre_dict[temp_key].append(i)

    return genre_dict

# 2.2
def calculate_average_rating(d):
    average_rating_dict = {}
    for i in d.keys():
        avg = sum(d[i])/len(d[i])
        avg = round(avg, 1)
        average_rating_dict[i] = avg        

    return average_rating_dict

# --- PART 3: RECOMMENDATION ---

# 3.1
def get_popular_movies(d, n=10):
    popular_movies = dict(sorted(d.items(), key = lambda x:x[1],reverse=True))
    if len(popular_movies) <= n:
        return popular_movies
    else:
        return dict(list(popular_movies.items())[:n])    
 
# 3.2
def filter_movies(d, thres_rating=3):
    filter_dict = {}
    for key, value in d.items():
        if value >= thres_rating:
            filter_dict[key] = value
    return filter_dict

# 3.3
def get_popular_in_genre(genre, genre_to_movies, movie_to_average_rating, n=5):
    popular_in_genre = {}
    temp = genre_to_movies[genre]
    for i in temp:
        popular_in_genre[i]=movie_to_average_rating[i]

    popular_in_genre = dict(sorted(popular_in_genre.items(), key = lambda x:x[1],reverse=True))
    if len(popular_in_genre) <= n:
        return popular_in_genre
    else:
        return dict(list(popular_in_genre.items())[:n])  


# 3.4
def get_genre_rating(genre, genre_to_movies, movie_to_average_rating):
    genre_ratings = {}
    list = genre_to_movies[genre]
    for i in movie_to_average_rating.keys():
        if i in list:
            genre_ratings[i] = movie_to_average_rating[i]
    avg = sum(genre_ratings.values())/len(genre_ratings)
    return avg

# 3.5
def genre_popularity(genre_to_movies, movie_to_average_rating, n=5):
    genres = genre_to_movies.keys()
    genre_rating = {}
    for i in genres:
        temp = get_genre_rating(i, genre_to_movies, movie_to_average_rating)
        genre_rating[i] = temp
    genre_rating = dict(sorted(genre_rating.items(), key = lambda x:x[1], reverse = True))
    if len(genre_rating) <= n:
        return genre_rating
    else:
        return dict(list(genre_rating.items())[:n])  
    

# --- PART 4: USER FOCUSED ---

# 4.1
def read_user_ratings(f):
    lines = []
    user_ratings_dict = {}
    with open (f, "r") as file:
        lines = file.readlines()
    file.close()

    for i in range(len(lines)):
        temp = lines[i][:-1].split("|")
        lines[i] = [temp[0], float(temp[1]), int(temp[2])]

    for i in lines:
        if i[2] not in user_ratings_dict:
            user_ratings_dict [i[2]] =[]
        temp = (i[0], i[1])
        user_ratings_dict [i[2]].append(temp)

    return user_ratings_dict

# 4.2
def get_user_genre(user_id, user_to_movies, movie_to_genre):
    list = user_to_movies[user_id]
    temp_dict = {}
    for name, rating in list:
        if movie_to_genre[name] not in temp_dict:
            temp_dict[movie_to_genre[name]] = []
        temp_dict[movie_to_genre[name]].append(rating)
    for key in temp_dict.keys():
        avg = sum(temp_dict[key])/len(temp_dict[key])
        temp_dict[key] = avg
    max_key = max(temp_dict, key=temp_dict.get)
    return max_key

# 4.3
def recommend_movies(user_id, user_to_movies, movie_to_genre, movie_to_average_rating):
    movie_list = user_to_movies[user_id]
    temp_dict = {}
    for name, rating in movie_list:
        if movie_to_genre[name] not in temp_dict:
            temp_dict[movie_to_genre[name]] = []
        temp_dict[movie_to_genre[name]].append(rating)
    for key in temp_dict.keys():
        avg = sum(temp_dict[key])/len(temp_dict[key])
        temp_dict[key] = avg
    genre = max(temp_dict, key=temp_dict.get)

    genre_list = []
    for key in movie_to_genre.keys():
        if movie_to_genre[key] == genre:
            genre_list.append(key)
    
    movie_list_1 = []
    for name, rating in movie_list:
        movie_list_1.append(name)

    genre_user_list = []
    for name in genre_list:
        if name not in movie_list_1:
            genre_user_list.append(name)

    recommend_dict = {}
    for name in genre_user_list:
        recommend_dict[name] = movie_to_average_rating[name]
    recommend_dict = dict(sorted(recommend_dict.items(), key = lambda x:x[1], reverse = True))

    return dict(list(recommend_dict.items())[:3]) 


# if __name__ == "__main__" :
#     ratings_dict = read_ratings_data("movieRatingSample.txt")
#     movie_genre_dict = read_movie_genre("genreMovieSample.txt")
#     genre_movie_dict = create_genre_dict(movie_genre_dict)
#     avg_rating_dict = calculate_average_rating(ratings_dict)

#     popular_movies = get_popular_movies(avg_rating_dict)
#     filter_dict = filter_movies(avg_rating_dict)
#     popular_in_genre = get_popular_in_genre("Comedy", genre_movie_dict, avg_rating_dict)
#     genre_rating = get_genre_rating("Comedy", genre_movie_dict, avg_rating_dict)
#     genre_pop = genre_popularity(genre_movie_dict, avg_rating_dict)
#     user_ratings = read_user_ratings("movieRatingSample.txt")
#     user_genre = get_user_genre(99999, user_ratings, movie_genre_dict)
#     recommend = recommend_movies(99999, user_ratings, movie_genre_dict, avg_rating_dict)
#     print(recommend)