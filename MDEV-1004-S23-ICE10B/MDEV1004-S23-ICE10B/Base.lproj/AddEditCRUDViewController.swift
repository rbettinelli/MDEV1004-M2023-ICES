import UIKit
import StripePaymentSheet

class AddEditAPICRUDViewController: UIViewController
{
    
    // UI References
    @IBOutlet weak var checkoutButton: UIButton!
    @IBOutlet weak var AddEditTitleLabel: UILabel!
    @IBOutlet weak var UpdateButton: UIButton!
    
    // Movie Fields
    @IBOutlet weak var movieIDTextField: UITextField!
    @IBOutlet weak var titleTextField: UITextField!
    @IBOutlet weak var studioTextField: UITextField!
    @IBOutlet weak var genresTextField: UITextField!
    @IBOutlet weak var directorsTextField: UITextField!
    @IBOutlet weak var writersTextField: UITextField!
    @IBOutlet weak var actorsTextField: UITextField!
    @IBOutlet weak var yearTextField: UITextField!
    @IBOutlet weak var lengthTextField: UITextField!
    @IBOutlet weak var descriptionTextView: UITextView!
    @IBOutlet weak var mpaRatingTextField: UITextField!
    @IBOutlet weak var criticsRatingTextField: UITextField!
    @IBOutlet weak var posterLinkTextField: UITextField!
    
    var movie: Movie?
    var crudViewController: APICRUDViewController? // Updated from MovieViewController
    var movieUpdateCallback: (() -> Void)? // Updated from MovieViewController
    var paymentSheet: PaymentSheet?
    let backendCheckoutUrl = URL(string: apiUrlString + "/payment")!
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        
        if let movie = movie
        {
            // Editing existing movie
            movieIDTextField.text = "\(movie.movieID)"
            titleTextField.text = movie.title
            studioTextField.text = movie.studio
            genresTextField.text = movie.genres.joined(separator: ", ")
            directorsTextField.text = movie.directors.joined(separator: ", ")
            writersTextField.text = movie.writers.joined(separator: ", ")
            actorsTextField.text = movie.actors.joined(separator: ", ")
            lengthTextField.text = movie.length
            yearTextField.text = "\(movie.year)"
            descriptionTextView.text = movie.shortDescription
            mpaRatingTextField.text = movie.mpaRating
            criticsRatingTextField.text = "\(movie.criticsRating)"
            posterLinkTextField.text = movie.posterLink
            
            AddEditTitleLabel.text = "Edit Movie"
            UpdateButton.setTitle("Update", for: .normal)
        }
        else
        {
            AddEditTitleLabel.text = "Add Movie"
            UpdateButton.setTitle("Add", for: .normal)
        }
        
        checkoutButton.addTarget(self, action: #selector(didTapCheckoutButton), for: .touchUpInside)
        checkoutButton.isEnabled = false
        
        fetchPaymentIntent()
    }
    
    func fetchPaymentIntent()
    {
        // Fetch the PaymentIntent client secret and publishable key
        var request = URLRequest(url: backendCheckoutUrl)
        request.httpMethod = "POST"
        let task = URLSession.shared.dataTask(with: request, completionHandler: { [weak self] (data, response, error) in
            guard let data = data,
                  let json = try? JSONSerialization.jsonObject(with: data, options: []) as? [String : Any],
                  let clientSecret = json["clientSecret"] as? String,
                  let publishableKey = json["publishableKey"] as? String,
                  let self = self else
            {
                // Handle errors here
                return
            }
            
            STPAPIClient.shared.publishableKey = publishableKey
            // Create and configure a PaymentSheet instance
            var configuration = PaymentSheet.Configuration()
            configuration.merchantDisplayName = "io-serv"
            configuration.defaultBillingDetails.address.country = "CA"
            configuration.allowsDelayedPaymentMethods = true
            self.paymentSheet = PaymentSheet(paymentIntentClientSecret: clientSecret, configuration: configuration)
            
        
            DispatchQueue.main.async
            {
                self.checkoutButton.isEnabled = true
            }
        })
        task.resume()
    }
            
            
    @objc
    func didTapCheckoutButton() {
        // MARK: Start the checkout process
        paymentSheet?.present(from: self) {
            paymentResult in
            switch paymentResult {
            case .completed:
                print("Your order is confirmed")
            case .canceled:
                print("Canceled!")
            case .failed(let error):
                print("Payment failed: \(error)")
            }
        }
    }
    
    @IBAction func CancelButton_Pressed(_ sender: UIButton)
    {
        dismiss(animated: true, completion: nil)
    }
    
    @IBAction func UpdateButton_Pressed(_ sender: UIButton)
    {
        guard let authToken = UserDefaults.standard.string(forKey: "AuthToken") else
        {
            print("AuthToken not available.")
            return
        }
        
        let urlString: String
        let requestType: String
        
        if let movie = movie {
            requestType = "PUT"
            urlString = apiUrlString + "/update/\(movie._id)"
        } else {
            requestType = "POST"
            urlString = apiUrlString + "/add"
        }
        
        guard let url = URL(string: urlString) else {
            print("Invalid URL.")
            return
        }
        
        // Explicitly mention the types of the data
        let id: String = movie?._id ?? UUID().uuidString
        let movieID: Int = Int(movieIDTextField.text ?? "") ?? 0
        let title: String = titleTextField.text ?? ""
        let studio: String = studioTextField.text ?? ""
        let genres: String = genresTextField.text ?? ""
        let directors: String = directorsTextField.text ?? ""
        let writers: String = writersTextField.text ?? ""
        let actors: String = actorsTextField.text ?? ""
        let year: Int = Int(yearTextField.text ?? "") ?? 0
        let length: String = lengthTextField.text ?? ""
        let shortDescription: String = descriptionTextView.text ?? ""
        let mpaRating: String = mpaRatingTextField.text ?? ""
        let criticsRating: Double = Double(criticsRatingTextField.text ?? "") ?? 0
        let posterLink: String = posterLinkTextField.text ?? ""

        // Create the movie with the parsed data
        let movie = Movie(
            _id: id,
            movieID: movieID,
            title: title,
            studio: studio,
            genres: [genres], // Wrap the value in an array
            directors: [directors], // Wrap the value in an array
            writers: [writers], // Wrap the value in an array
            actors: [actors], // Wrap the value in an array
            year: year,
            length: length,
            shortDescription: shortDescription,
            mpaRating: mpaRating,
            criticsRating: criticsRating,
            posterLink: posterLink
        )
        
        var request = URLRequest(url: url)
        request.httpMethod = requestType
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        // Add the AuthToken to the request headers
        request.setValue("Bearer \(authToken)", forHTTPHeaderField: "Authorization")
        
        do {
            request.httpBody = try JSONEncoder().encode(movie)
        } catch {
            print("Failed to encode movie: \(error)")
            return
        }
        
        let task = URLSession.shared.dataTask(with: request) { [weak self] data, response, error in
            if let error = error
            {
                print("Failed to send request: \(error)")
                return
            }
            
            DispatchQueue.main.async
            {
                self?.dismiss(animated: true)
                {
                    self?.movieUpdateCallback?()
                }
            }
        }
        
        task.resume()
    }
}
