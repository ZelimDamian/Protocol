

directory.Customer = Backbone.Model.extend({

    urlRoot:"/rest/bank/clients",

    defaults:function() {
    	return {
    		login : '',
    		account : ''
    	};
    }
});

directory.CustomerCollection = Backbone.Collection.extend({

    model: directory.Customer,

    url:"/rest/bank/clients"

});