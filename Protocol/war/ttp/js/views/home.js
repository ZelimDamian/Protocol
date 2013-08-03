directory.HomeView = Backbone.View.extend({

	el : '#homeContainer',
	
	initialize : function(options)
	{
		_.bindAll(this);

		this.searchResults = options.searchResults;
        this.trustedPartyView = new directory.TrustedPartyView(); 
	},

    events:{
        'click #saveProduct': 'saveProduct',
        'click #hideHeroButton': 'removeHero'
    },

    render:function () {
        this.$el.html(this.template());
        this.$el.find('.trustedPartyView').html(this.trustedPartyView.render().el);
        
        return this;
    },

    removeHero: function()
    {
    	this.$('.hero-unit').slideUp();
    },
    
    newProduct:function(){
    	
    	var product = new directory.Product();
    	this.productSummaryView.setModel(product);
    },
    
    renderProduct:function(id){
    	var product = this.searchResults.get(id);
    	if(!product) {
    		product = new directory.Product({id:id});
    		product.fetch();
		}
    	this.productSummaryView.setModel(product);
    	this.productSummaryView.render();
	},
    
	saveProduct: function()
	{
		this.productSummaryView.updateSaveModel();
	},
	
    showMeBtnClick:function () {
        directory.shellView.search();
    }

});