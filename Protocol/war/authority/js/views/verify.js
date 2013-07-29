directory.CertificateSummaryView = Backbone.View.extend({

    initialize:function (options) {
    	_.bindAll(this);
    	this.model = new directory.Product();
        this.model.on("change", this.render, this);
        this.collection = options.collection;
    },

    render:function () {
    	
        this.$el.html(this.template(_.extend(this.model.toJSON())));

        return this;
    },
    
    showModel:function(model)
    {
    	this.model.attributes = _.extend(this.model.attributes, model);
    	return this.render().el;
    },
    

});

directory.VerifyView = Backbone.View.extend({

	el : '#homeContainer',
	
	initialize : function(options)
	{
		_.bindAll(this);

		this.searchResults = options.searchResults;
        this.certificateSummaryView = new directory.CertificateSummaryView({collection:this.searchResults});
	},

    events:{
        'click #verifyProduct': 'verifyProduct',
    },

    render:function () {
        this.$el.html(this.template());
        this.$el.find('.product-container').html(this.certificateSummaryView.render().el);
        return this;
    },

    verifyProduct:function()
    {
    	var cert = this.$('#cert').val().replace(' ', '');
    	this.doVerification(cert);
    },
    
    doVerification: function(cert)
    {
    	$.get("/rest/authority/verify/" + cert).done(this.verifyCallback).fail(this.errorCallback);
    },
    
    verifyCallback: function(verification,status){
    	verification.found = 'success';
    	this.$el.find('.product-container').html(this.certificateSummaryView.showModel(verification));
	  },
    
	errorCallback:function(status){
    	this.$el.find('.product-container').html(this.certificateSummaryView.showModel({ found : 'error' }));
	},
	  
    renderProduct:function(id){
    	var product = this.searchResults.get(id);
    	if(!product)
    	{
    		product = new directory.Product({id:id});
    		product.fetch();
		}
    	this.productSummaryView.setModel(product);
    	this.productSummaryView.render();
	},


});