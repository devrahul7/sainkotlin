import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.freshmart.repository.ProductRepositoryImpl
import com.example.freshmart.viewmodel.ProductViewModel

class ProductViewModelFactory(private val repo: ProductRepositoryImpl) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(repo) as T
    }
}
