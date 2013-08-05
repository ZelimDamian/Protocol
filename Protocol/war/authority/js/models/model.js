

directory.Product = Backbone.Model.extend({

    urlRoot:"/rest/authority/products",

    defaults:function() {
    	return {
    		name : '',
    		description : '',
    		price : '',
    		cert : '',
    		merchant: '',
    		found : '',
    		productDescription: '',
    		productName: '',
    		content: ''
    	};
    }
});

directory.ProductCollection = Backbone.Collection.extend({

    model: directory.Product,

    url:"/rest/authority/products"

});