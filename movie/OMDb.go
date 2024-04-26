package movie

import (
	"encoding/json"
	"errors"
	"net/http"
	"net/url"
)

type SearchType int

type SearchResult struct {
	Title    string
	Year     string
	Released string
	Runtime  string
	Actors   string
	Poster   string
	Language string
}

const (
	Title = iota
	IMDbID
	Kk
)

const apiUrl = "https://www.omdbapi.com"
const apiKey = "ae66075e"

func (s SearchType) String() string {
	switch s {
	case Title:
		return "title"
	case IMDbID:
		return "IMDbID"
	default:
		return "Unknown"
	}
}

func Search(params string, sType SearchType) (*SearchResult, error) {
	q := url.QueryEscape(params)
	var query string

	if sType == Title {
		query = searchByTitle(q)
	} else if sType == IMDbID {
		query = searchByIMDbID(q)
	} else {
		return nil, errors.New("error search type: " + sType.String())
		//	return nil
	}
	resp, err := http.Get(query)
	if resp.StatusCode != http.StatusOK {
		resp.Body.Close()
		return nil, errors.New("search query failed: " + resp.Status)
	}
	var result SearchResult
	if err := json.NewDecoder(resp.Body).Decode(&result); err != nil {
		resp.Body.Close()
		return nil, err
	}
	err = resp.Body.Close()
	if err != nil {
		return nil, err
	}
	return &result, nil
}

func searchByTitle(title string) string {
	return getApiUrl() + "&t=" + title
}

func searchByIMDbID(id string) string {
	return getApiUrl() + "&i=" + id

}

func getApiUrl() string {
	return apiUrl + "/?apikey=" + apiKey
}
