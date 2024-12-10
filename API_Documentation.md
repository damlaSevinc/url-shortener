# API Documentation

## Overview

This document provides details about the API endpoints available in the application.

## Endpoints Summary

| Method | Endpoint               | Description                                      | Parameters                      |
|--------|------------------------|--------------------------------------------------|---------------------------------|
| POST   | `/api/urls`            | Shorten an original URL and save it              | `UrlRequestDto` (object) (body) |
| GET    | `/api/urls/{shortUrl}` | Resolve a short URL to retrieve the original URL | `shortUrl` (path)               |

## Endpoint Details

### POST `/api/urls`

**Description**: Accepts an original URL and returns a shortened version of the URL. The response contains the shortened **slug** that can be appended to the base URL to access the original URL.

**Request**:  Body: A JSON object with the original URL.

```json
{
  "originalUrl": "https://www.example.com"
}
```

**Response**:

- Status: 201 Created
- Body: A JSON object with the shortened URL. OriginalUrl is optional if needed.

```json
{
  "shortUrl": "e149be1"
}
```

### GET `/api/urls/{shortUrl}`

**Description**: Resolves a short URL to retrieve the original URL.

**Request**: Path variable: The shortened URL slug. (e.g., `e149be1`)

**Response**:

- Status: 200 OK
- Body: A JSON object with the original URL. ShortUrl is optional if needed.

```json
{
  "originalUrl": "https://www.example.com"
}
```

## Response Status Codes

- **200 OK**: The request was successful.

{
  "originalUrl": "https://www.example.com"
}

shortUrl (string): The slug (7-character hash) representing the shortened URL. This slug can be appended to any base URL (e.g., https://short.ly/e149be1) to access the original URL.

- **400 Bad Request**: The request could not be understood or was missing required parameters.

{
  "error": "Invalid URL format"
}

- **301 Moved Permanently**: The requested resource has been assigned a new permanent URI and any future references to this resource should use one of the returned URIs. (for the redirection)

- **404 Not Found**: The requested resource could not be found.

{
  "error": "Short URL not found"
}

- **500 Internal Server Error**: An error occurred on the server.
