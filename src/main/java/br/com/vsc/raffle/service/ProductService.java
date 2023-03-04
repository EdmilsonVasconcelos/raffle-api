package br.com.vsc.raffle.service;

import br.com.vsc.raffle.exception.ProductAlreadyExistsException;
import br.com.vsc.raffle.exception.ProductDoesNotExistException;
import br.com.vsc.raffle.model.Product;
import br.com.vsc.raffle.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductDoesNotExistException("Produto não existe"));
    }

    public Product upsertProduct(Product product) {
        if(product.getId() == null){
            validateIfProductAlreadyExists(product.getDescription());
        }

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = findProductById(id);
        productRepository.delete(product);
    }

    private void validateIfProductAlreadyExists(String description) {
        Optional<Product> product = productRepository.findByDescription(description);

        if(product.isPresent()) {
            throw new ProductAlreadyExistsException("Produto já existe");
        }
    }
}
