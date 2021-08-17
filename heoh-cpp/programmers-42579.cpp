#include <string>
#include <vector>
#include <map>
#include <algorithm>

using namespace std;

struct Song {
	int id;
	string genre;
	int play;
};

struct Genre {
	string name;
	int totalPlay;
	vector<Song*> songs;
};

vector<Genre> createGenres(vector<string> genreNames) {
	vector<Genre> genres;
	for (string name : genreNames)
		genres.push_back({ name, 0 });
	return genres;
}

vector<Song> createSongs(vector<string> genreNames, vector<int> plays) {
	vector<Song> songs;
	for (int i = 0; i < plays.size(); i++) {
		songs.push_back({ i, genreNames[i], plays[i] });
	}
	return songs;
}

map<string, Genre*> createGenreMap(vector<Genre>& genres) {
	map<string, Genre*> genreMap;
	for (Genre& genre : genres) {
		genreMap[genre.name] = &genre;
	}
	return genreMap;
}

void registerSongsToGenre(vector<Song>& songs, vector<Genre>& genres) {
	map<string, Genre*> genreMap = createGenreMap(genres);
	for (Song& song : songs) {
		Genre& genre = *genreMap[song.genre];
		genre.songs.push_back(&song);
		genre.totalPlay += song.play;
	}
}

vector<int> pickBestNSongsEachGenre(vector<Genre> genres, int nSongsPerGenre) {
	vector<int> songIds;

	sort(genres.begin(), genres.end(), [](const auto& lhs, const auto& rhs) {
		return lhs.totalPlay > rhs.totalPlay;
		});

	for (Genre& genre : genres) {
		sort(genre.songs.begin(), genre.songs.end(), [](const auto& lhs, const auto& rhs) {
			if (lhs->play != rhs->play) {
				return lhs->play > rhs->play;
			}
			return lhs->id < rhs->id;
			});

		for (int i = 0; i < genre.songs.size() && i < 2; i++) {
			songIds.push_back(genre.songs[i]->id);
		}
	}

	return songIds;
}

vector<int> solution(vector<string> genreNames, vector<int> plays) {
	vector<Genre> genres = createGenres(genreNames);
	vector<Song> songs = createSongs(genreNames, plays);
	registerSongsToGenre(songs, genres);
	vector<int> bestSongIds = pickBestNSongsEachGenre(genres, 2);
	return bestSongIds;
}
