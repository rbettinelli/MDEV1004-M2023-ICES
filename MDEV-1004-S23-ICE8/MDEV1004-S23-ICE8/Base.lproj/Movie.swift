struct Movie: Codable
{
    let _id: String
    let movieID: Int
    let title: String
    let studio: String
    let genres: [String]
    let directors: [String]
    let writers: [String]
    let actors: [String]
    let year: Int
    let length: String
    let shortDescription: String
    let mpaRating: String
    let criticsRating: Double
    let posterLink: String
}
