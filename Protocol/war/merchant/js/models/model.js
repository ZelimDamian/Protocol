

directory.Product = Backbone.Model.extend({

    urlRoot:"/rest/merchant/products",

    defaults:function() {
    	return {
    		name : '',
    		description : '',
    		price : '',
    		cert : '',
    		content: ''
    	};
    }
});

directory.ProductCollection = Backbone.Collection.extend({

    model: directory.Product,

    url:"/rest/merchant/products"

});