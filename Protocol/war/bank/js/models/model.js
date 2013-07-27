

directory.Customer = Backbone.Model.extend({

    urlRoot:"/rest/bank/customers",

    defaults:function() {
    	return {
    		login : '',
    		account : ''
    	};
    }
});

directory.CustomerCollection = Backbone.Collection.extend({

    model: directory.Customer,

    url:"/rest/bank/customers"

});